package com.mani.theatre.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mani.theatre.beans.PayPalBean;
import com.mani.theatre.utilities.ConnectionHandler;

public class PayPalDAO {
	
	private String hashPassword(String password) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			String salt = "";
			String passWithSalt = password + salt;
			byte[] passHash = sha256.digest(passWithSalt.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < passHash.length; i++)
				sb.append(Integer.toString((passHash[i] & 0xff), 16).substring(1));
			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean registerPayPal(PayPalBean bean)
	{
		Connection conn=ConnectionHandler.getConnection();
		try {
			PreparedStatement pst=conn.prepareStatement("INSERT INTO `rt`.`paypal` (`userid`,`password`,`balance`) VALUES (?,?,?)");
			System.out.println(hashPassword(bean.getPassword()));
			pst.setString(1, bean.getUserName());
			pst.setString(2,hashPassword(bean.getPassword()));
			pst.setFloat(3, bean.getBalance());
			if(pst.executeUpdate()!=0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean validatePayPalAndPay(PayPalBean bean)
	{
		Connection conn=ConnectionHandler.getConnection();
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement("select true from rt.paypal where userid=? and password= ? and balance >= ?");
			pst.setString(1, bean.getUserName());
			pst.setString(2, hashPassword(bean.getPassword()));
			pst.setFloat(3, bean.getBalance());
			if(pst.executeQuery().next())
				if(pst.executeUpdate("update rt.paypal set balance=balance-"+bean.getBalance()+" where userid='"+bean.getUserName()+"'")!=0)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
