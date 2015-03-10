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
$sql2 = "select * from userJoined"; 
$result = mysql_query($sql);
$result2 = mysql_query($sql2);
$json = array();
$json2 = array(); 
if(mysql_num_rows($result)){
while($row=mysql_fetch_assoc($result)){
$json['userInfo'][]=$row;
}
}



if(mysql_num_rows($result2)){
while($row=mysql_fetch_assoc($result2)){
$json['userInfo'][]=$row;
}
}




mysql_close($con);
echo json_encode($json);
echo json_encode($json2); 
?> 
