package com.twitter.lib;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

	import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;



	public class Dbutils {

	private static final void listContext(Context ctx, String indent) {
	try {
	NamingEnumeration list = ctx.listBindings("");
	while (list.hasMore()) {
	Binding item = (Binding) list.next();
	String className = item.getClassName();
	String name = item.getName();
	System.out.println("" + className + " " + name);
	Object o = item.getObject();
	if (o instanceof javax.naming.Context) {
	listContext((Context) o, indent + " ");
	}
	}
	} catch (NamingException ex) {
	System.out.println("JNDI failure: " + ex);
	}
	}

	/**
	* Assembles a DataSource from JNDI.
	*/
	public DataSource assemble(ServletConfig config) throws ServletException {
	DataSource _ds = null;
	String dataSourceName = config.getInitParameter("data-source");
	System.out.println("Data Source Parameter" + dataSourceName);
	if (dataSourceName == null)
	throw new ServletException("data-source must be specified");
	Context envContext = null;
	try {
	Context ic = new InitialContext();
	System.out.println("initial context " + ic.getNameInNamespace());
	envContext = (Context) ic.lookup("java:/comp/env");
	System.out.println("envcontext " + envContext);
	listContext(envContext, "");
	} catch (Exception et) {
	throw new ServletException("Can't get contexts " + et);
	}
	// _ds = (DataSource) ic.lookup("java:"+dataSourceName);
	// _ds = (DataSource) ic.lookup("java:comp/env/" );
	try {
	_ds = (DataSource) envContext.lookup(dataSourceName);

	if (_ds == null)
	throw new ServletException(dataSourceName
	+ " is an unknown data-source.");
	} catch (NamingException e) {
	throw new ServletException("Cant find datasource name " +dataSourceName+" Error "+ e);
	}
	CreateSchema(_ds);
	return _ds;

	}

	// create the schema if it doesn't exist
	private void CreateSchema(DataSource _ds) {
	PreparedStatement pmst = null;
	Connection Conn;
	
	try {
	Conn = _ds.getConnection();
	} catch (Exception et) {
	return;
	}

	String sqlQuery = "CREATE TABLE IF NOT EXISTS `Users` ("
	+ "`idUsers` INT NOT NULL AUTO_INCREMENT," + "`email` VARCHAR(45) NULL,"
	+ "`password` VARCHAR(45) NULL," + "`username` VARCHAR(20) NULL,"
	+ "PRIMARY KEY (`idUsers`))" + "ENGINE = InnoDB;";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not create table "+ex);
	return;
	}
	
	sqlQuery = "CREATE TABLE IF NOT EXISTS `Tweets` ("
	+ "`idTweets` INT NOT NULL AUTO_INCREMENT," + " `tweet` VARCHAR(120) NULL,"
	+ "`user` INT NULL," + "`time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
	+ "PRIMARY KEY (`idTweets`)," + " INDEX `userid_idx` (`user` ASC),"
	+ "CONSTRAINT `user`" + " FOREIGN KEY (`user`)"
	+ " REFERENCES `Users` (`idUsers`)" + "ON DELETE NO ACTION"
	+ " ON UPDATE NO ACTION)"
	+ "ENGINE = InnoDB;";

	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not create table "+ex);
	return;
	}
	sqlQuery = "CREATE TABLE IF NOT EXISTS `Following` ("
	+ "`idFollowing` INT NOT NULL AUTO_INCREMENT," + "`followuserid` INT NULL,"
	+ "`flwinguserid` INT NULL,"
	+ "PRIMARY KEY (`idFollowing`),"
	+ "INDEX `userid_idx` (`followuserid` ASC),"
	+ "INDEX `fkfollowing_idx` (`flwinguserid` ASC),"
	+ "CONSTRAINT `userid`"
	+ "FOREIGN KEY (`followuserid`)"
	+ "REFERENCES `Users` (`idUsers`)"
	+ "ON DELETE NO ACTION" + " ON UPDATE NO ACTION,"
	+ "CONSTRAINT `fkfollowing`"
	+ " FOREIGN KEY (`flwinguserid`)" + " REFERENCES `Users` (`idUsers`)"
	+ "ON UPDATE NO ACTION)" + "ENGINE = InnoDB;";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not create table "+ex);
	return;
	}
	ResultSet rs=null;
	sqlQuery="Select count(email) from Users as rowcount";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	rs=pmst.executeQuery();
	if(rs.next()) {
	int rows = rs.getInt(1);
	System.out.println("Number of Rows " + rows);
	if (rows==0){
	sqlQuery="INSERT INTO `Users` (`email`, `password`, `username`) VALUES ('m@je.d', '1234', 'majedarsenal');";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert email in Users "+ex);
	return;
	}
	
	
	sqlQuery="INSERT INTO `Users` (`email`, `password`, `username`) VALUES ('test@test.com', '999', 'testuser');";
try {
pmst = Conn.prepareStatement(sqlQuery);
pmst.executeUpdate();
} catch (Exception ex) {
System.out.println("Can not insert stuff in users "+ex);
return;
}


sqlQuery="INSERT INTO `Users` (`email`, `password`, `username`) VALUES ('user@user.uk', 'testpass', 'MrBlabby');";
try {
pmst = Conn.prepareStatement(sqlQuery);
pmst.executeUpdate();
} catch (Exception ex) {
System.out.println("Can not insert names in authors "+ex);
return;
}


	sqlQuery="INSERT INTO `Tweets` (`tweet`, `user`) VALUES ('Test tweet one', 1);";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert names in sections "+ex);
	return;	
	}
	
	sqlQuery="INSERT INTO `Tweets` (`tweet`, `user`) VALUES ('Test tweet two', 2)";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert names in sections "+ex);
	return;	
	}
	
	sqlQuery="INSERT INTO `Tweets` (`tweet`, `user`) VALUES ('Zzzzzzzzzzzzzzzzzzzzzzzzzzzzz', 3)";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert names in sections "+ex);
	return;	
	}
	
	sqlQuery="INSERT INTO `Following` (`followuserid`, `flwinguserid`) VALUES (1,2)";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert default fault "+ex);
	return;	
	}
	
	sqlQuery="INSERT INTO `Following` (`followuserid`, `flwinguserid`) VALUES (2,3)";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert default fault "+ex);
	return;	
	}
	
	sqlQuery="INSERT INTO `Following` (`followuserid`, `flwinguserid`) VALUES (2,1)";
	try {
	pmst = Conn.prepareStatement(sqlQuery);
	pmst.executeUpdate();
	} catch (Exception ex) {
	System.out.println("Can not insert default fault "+ex);
	return;	
	}
	}
	}

	} catch (Exception ex) {
	System.out.println("Can not select count "+ex);
	return;
	}


	 

	}
	
	public void createSchema(){
	String url = "jdbc:mysql://localhost";
	Connection conn=null;
	try {
	Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	conn = DriverManager.getConnection (url, "root", "Cl1m8t3;");
	
	}catch (Exception et){
		System.out.println("Can't get conenction to create schema "+et);
		return;
	}
		String sqlcreateSchema="Create database if not exists twitter ;";
	try{
		java.sql.Statement statement=conn.createStatement();
		statement.execute(sqlcreateSchema);
	conn.close();
	}catch (Exception et){
		System.out.println("MajedMonemTwitter Can not create schema ");
	return;
	}
	
	}
}
