package com.mani.theatre.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mani.theatre.utilities.ConnectionHandler;

public class TheatreConfigurerDAO {

	public boolean configureTheatre(int screens, int rows, int seats) {
		System.out.println("Executing starts here");
		Connection conn = ConnectionHandler.getConnection();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			if (st.executeQuery("Select * from rt.screen").next()) {
				return false;
			}
			if (st.executeUpdate("update rt.theatre set number_of_screens=" + screens + " where id=1") > 0) {
				for (int i = 1; i <= screens; i++) {
					System.out.println("insert into rt.screen (id,name,number_of_seats) values ('S" + i + "',"
							+ rows * seats + ")");
					int result=st.executeUpdate("insert into rt.screen (id,name,number_of_seats) values ("+i+",'S" + i+ "',"
							+ rows * seats + ")");
					System.out.println("Result::::"+result);
					if (result == 0) {
						System.out.println("Failed at inserting screens");
						conn.rollback();
						return false;
					}
				}
				for (int i = 1; i <= screens; i++) {
					for (int j = 1; j <= rows; j++) {
						for (int k = 1; k <= seats; k++) {
							System.out.println("INSERT INTO `rt`.`seat`(`row_id`,`number_id`,`screen_id`) VALUES ("
									+ j + "," + k + "," + i + ")");
							if (st.executeUpdate("INSERT INTO `rt`.`seat`(`row_id`,`number_id`,`screen_id`) VALUES ("
									+ j + "," + k + "," + i + ")") == 0) {
								System.out.println("Failed at inserting into seats");
								conn.rollback();
								return false;
							}
						}
					}
				}
				conn.commit();
				conn.close();
				return true;
			}
			conn.rollback();
			conn.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
