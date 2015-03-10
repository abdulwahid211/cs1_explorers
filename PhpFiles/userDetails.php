<?php
/*
Reference: http://www.tutorialspoint.com/php/mysql_select_php.htm

*/

error_reporting(E_ALL ^ E_DEPRECATED);
//import and connect to MySQL database stuff
require("configuration.php");

 
$con=mysql_connect("$host", "$username", "$password")or die("failed connection"); 
mysql_select_db("$dbname")or die("failed to connect to the database");
$sql = "select * from userInfo"; 
$result = mysql_query($sql);
$json = array();
 
if(mysql_num_rows($result)){
while($row=mysql_fetch_assoc($result)){
$json['userInfo'][]=$row;
}
}
mysql_close($con);
echo json_encode($json); 
?> 
