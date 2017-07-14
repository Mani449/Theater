package com.mani.theatre.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mani.theatre.beans.Screen;
import com.mani.theatre.beans.Seat;
import com.mani.theatre.utilities.ConnectionHandler;

public class BookTicketsDAO {

	private HashMap<Integer, Character> rowMap = new HashMap<>(10);

	public BookTicketsDAO() {
		rowMap.put(1, 'A');
		rowMap.put(2, 'B');
		rowMap.put(3, 'C');
		rowMap.put(4, 'D');
		rowMap.put(5, 'E');
		rowMap.put(6, 'F');
		rowMap.put(7, 'G');
		rowMap.put(8, 'H');
		rowMap.put(9, 'I');
		rowMap.put(10, 'J');
		rowMap.put(11, 'K');
	}

	public List<Screen> getCancelScreens() {
		ArrayList<Screen> screens = new ArrayList<Screen>();
		Connection conn = ConnectionHandler.getConnection();
		try {
			ResultSet rs = conn.createStatement().executeQuery(
					"select screen_id,r.name from rt.seat_reserved s,rt.screening c,rt.screen r where s.screening=c.id and r.id=screen_id group by screen_id");
			while (rs.next()) {
				screens.add(new Screen(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return screens;
	}

	public List<Screen> getScreens() {
		ArrayList<Screen> screens = new ArrayList<Screen>();
		Connection conn = ConnectionHandler.getConnection();
		try {
			ResultSet rs = conn.createStatement().executeQuery(
					"select *from rt.screen where id in (SELECT screen_id FROM rt.seat s left outer join rt.seat_reserved r  on s.id=r.seat_id where r.seat_id is null group by screen_id having count(*)>0)");
			while (rs.next()) {
				screens.add(new Screen(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return screens;
	}

	public int availableSeats(int screenId) {
		Connection conn = ConnectionHandler.getConnection();
		try {
			Statement st = conn.createStatement();
			System.out.println(
					"SELECT count(*) FROM rt.seat s left outer join rt.seat_reserved r on s.id=r.seat_id where s.screen_id="
							+ screenId + "and r.id is null");
			ResultSet rs = st.executeQuery(
					"SELECT count(*) FROM rt.seat s left outer join rt.seat_reserved r on s.id=r.seat_id where s.screen_id="
							+ screenId + " and r.seat_id is null");
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<Seat> getAllSeats(int screenid) {
		Connection conn = ConnectionHandler.getConnection();
		ArrayList<Seat> seats = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();

			String sql = "SELECT s.id,s.row_id,s.number_id, r.reservation_id FROM rt.seat s left outer join rt.seat_reserved r on s.id=r.seat_id where s.screen_id="
					+ screenid + " order by s.id";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Seat seat = new Seat(rowMap.get(rs.getInt("row_id")) + rs.getString("number_id"), screenid,
						(rs.getString("reservation_id") == null) ? true : false, rs.getInt("id"));
				seats.add(seat);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seats;
	}

	public boolean cancelSeats(List<Integer> seats, int screen) {

		Connection conn = ConnectionHandler.getConnection();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select id from screening where screen_id=" + screen);
			int screeningId;
			if (rs.next())
				screeningId = rs.getInt(1);
			else {
				conn.rollback();
				conn.close();
				return false;
			}
			synchronized (new Integer(screen)) {

				String query = "INSERT INTO `rt`.`reservation` (`screening_id`,`reservation_type`,`reserved`,`date_tx`,`no_of_tickets`,`price`) VALUES	("
						+ screeningId + ",'Admin Cancel Tickets'," + true + ",now()," + seats.size() + ","
						+ seats.size() * -10 + ")";
				if (st.executeUpdate(query) == 0) {
					conn.rollback();
					conn.close();
					return false;
				}
			}

			for (int seat : seats) {
				synchronized (new Integer(seat)) {
					if (st.executeUpdate("delete from `rt`.`seat_reserved` where seat_id=" + seat) == 0) {
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}
			conn.commit();
			conn.close();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public int bookSeats(int seats, int screen) {

		Connection conn = ConnectionHandler.create();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select id from screening where screen_id=" + screen);
			int screeningId;
			if (rs.next())
				screeningId = rs.getInt(1);
			else {
				conn.rollback();
				conn.close();
				return -1;
			}
			int reservation_id;
			synchronized (new Integer(screen)) {

				String query = "INSERT INTO `rt`.`reservation` (`screening_id`,`reservation_type`,`reserved`,`date_tx`,`no_of_tickets`,`price`) VALUES	("
						+ screeningId + ",'online'," + false + ",now()," + seats + "," + seats * 10 + ")";
				if (st.executeUpdate(query) == 0) {
					conn.rollback();
					conn.close();
					return -1;
				}
				rs = st.executeQuery("select max(id) id from rt.reservation");
				rs.next();
				reservation_id = rs.getInt("id");
			}
			conn.commit();
			conn.close();			
			return reservation_id;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return -1;
	}

	public boolean finalizeTransaction(List<Integer> seats,int reservation_id, int screen) {
		Connection conn = ConnectionHandler.create();
		synchronized (conn) {

			try {
				conn.setAutoCommit(false);

				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select id from screening where screen_id=" + screen);
				int screeningId;
				if (rs.next())
					screeningId = rs.getInt(1);
				else {
					conn.rollback();
					conn.close();
					return false;
				}
				for (int seat : seats) {
					synchronized (new Integer(seat)) {
						if (st.executeUpdate(
								"INSERT INTO `rt`.`seat_reserved` (`seat_id`,`reservation_id`,`screening`) VALUES (" + seat
										+ "," + reservation_id + "," + screeningId + ")") == 0) {
							conn.rollback();
							conn.close();
							return false;
						}
					}
				}
				
				System.out.println("update `rt`.`reservation` set reserved=true where id=" + reservation_id);
				if (st.executeUpdate("update `rt`.`reservation` set reserved=true where id=" + reservation_id) == 0) {
					deleteTickets(reservation_id);
					conn.rollback();
					conn.close();
					return false;
				}
				conn.commit();
				conn.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public int deleteTickets(int reservation_id) {
		Connection conn = ConnectionHandler.getConnection();
		try {
			Statement st = conn.createStatement();
			return st.executeUpdate("delete from rt.seat_reserved where reservation_id=" + reservation_id);
		} catch (SQLException exp) {
			System.out.println(exp.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public float getCostReservation(int reservationid) {
		Connection conn = ConnectionHandler.create();
		synchronized (conn) {
			try {
				Statement st = conn.createStatement();

				ResultSet rs = st.executeQuery("Select price from rt.reservation where id=" + reservationid);
				System.out.println("Select price from rt.reservation where id=" + reservationid);
				if (rs.next()) {
					float price = rs.getFloat("price");
					System.out.println("^&$&%^&^&#$^&#% Price!!!!!!!!!11 " + price);
					return price;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
		}
	}
}
