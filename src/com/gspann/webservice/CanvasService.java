package com.gspann.webservice;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/AstraCanvasService")
public class CanvasService 
{
	@GET
	@Path("canvasID/{canvasID}")
	@Produces(MediaType.TEXT_PLAIN)
	 public String isCanvasIDValid(@PathParam("canvasID") String canvasID) 
	 {
		 	String IP="";
	        String dbName="";
	        String usrName="";
	        String pwd="";
	        Properties props = new Properties();
		
		String sqlString = "SELECT * FROM CANVAS_LAYOUT WHERE CANVAS_ID="+canvasID;
	      System.out.println("Sql String :"+sqlString);
	      Connection connection = null;
	      try {
	    	    props.load(new FileInputStream("/home/iwov/Interwoven/TeamSite/custom/bin/scripts/macysdotcom/inline/dbConfig.properties"));
	    	    IP = props.getProperty("IP");
	    	    dbName = props.getProperty("dbName");
	    	    usrName = props.getProperty("usrName");
	    	    pwd = props.getProperty("pwd");

	    	    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    	    //Change the db connection string based on the environment
	    	    //connection = DriverManager.getConnection("jdbc:oracle:thin:cmsusr/cmsusr@11.120.184.77:1521:starsdev");
	    	    connection  = DriverManager.getConnection("jdbc:oracle:thin:@"+IP+"/"+""+dbName+"",""+usrName+"",""+pwd+"");
	    	    System.out.println("connection------------>"+connection);
	      }
	      catch (Exception e) 
	      {
	          e.printStackTrace();
	      }
	      try {
	          Statement stmt = connection.createStatement();
	          ResultSet rs = stmt.executeQuery(sqlString);
	          System.out.println("inside result set");
	          String canvasId = "";
              System.out.println("resultset--------------->"+rs);
	          while (rs.next()) {
	              canvasId = rs.getString(1);
	          }
	          if (canvasId.length() > 0)
	          {
	        	  System.out.println("id exist");
	              return "id exist";
	          }
	          else
	          {
	              return "id not exist";
	          }
	      }catch (Exception e) {
	          throw new WebApplicationException(e);
	      } 
	      /*finally {
	          try {
	              if (connection != null)
	                  connection.close();
	          } catch (SQLException e) {
	              e.printStackTrace();
	              throw new WebApplicationException(e);
	          }
	      }*/
		
	 }
	@GET
	@Path("/hello")
	  @Produces(MediaType.TEXT_HTML)
	  public String sayHtmlHello() {
	    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
	        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	  }
	

}
