<?php 
/* test123

****   THIS IS PHP SCRIPT HAS BEEN ADOPTED FROM MY BRING BACK FROM YOUTUBE TUTORIALS ****

REFERENCE : 
https://www.youtube.com/watch?v=mQGQADq3iOQ


*/
error_reporting(E_ALL ^ E_DEPRECATED); // show errors 
    
 // variable to define mySQL database 
    $username = "wadz"; 
    $password = "abzy211"; 
    $host = "localhost"; 
    $dbname = "test"; 
    $dbTable ="abz";


    
    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'); 
     
   
    try 
    { 
       
        $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8", $username, $password, $options); 
    } 
    catch(PDOException $ex) 
    { 
         
        die("Failed to connect to the database: " . $ex->getMessage()); 
    } 
     
  
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 
     

    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC); 
     
    // This block of code is used to undo magic quotes.  Magic quotes are a terrible 
    // feature that was removed from PHP as of PHP 5.4.  However, older installations 
    // of PHP may still have magic quotes enabled and this code is necessary to 
    // prevent them from causing problems.  For more information on magic quotes: 
    // http://php.net/manual/en/security.magicquotes.php 
    if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()) 
    { 
        function undo_magic_quotes_gpc(&$array) 
        { 
            foreach($array as &$value) 
            { 
                if(is_array($value)) 
                { 
                    undo_magic_quotes_gpc($value); 
                } 
                else 
                { 
                    $value = stripslashes($value); 
                } 
            } 
        } 
     
        undo_magic_quotes_gpc($_POST); 
        undo_magic_quotes_gpc($_GET); 
        undo_magic_quotes_gpc($_COOKIE); 
    } 
     
   
    header('Content-Type: text/html; charset=utf-8'); 
     
   
    // http://us.php.net/manual/en/book.session.php 
    session_start(); 

    // Note that it is a good practice to NOT end your PHP files with a closing PHP tag. 
    // This prevents trailing newlines on the file from being included in your output, 
    // which can cause problems with redirecting users.

?>

