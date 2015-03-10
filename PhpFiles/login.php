<?php

/*

****   THIS IS PHP SCRIPT HAS BEEN ADOPTED FROM MY BRING BACK FROM YOUTUBE TUTORIALS ****

REFERENCE : 
https://www.youtube.com/watch?v=mQGQADq3iOQ


*/

//import and connect to MySQL database stuff
require("configuration.php");

// query for the user login
    $query = " 
            SELECT 
                id, 
                username, 
                password
            FROM userInfo 
            WHERE 
                username = :username 
        ";
    
    $query_params = array(
        ':username' => $_POST['username']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
    
		
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
    
    
    $validated_info = false;
    $valid_login = null;
    //fetching all the users details from the query
    $logon_details = $stmt->fetch();
    if ($logon_details) {
        
		
        if ($_POST['password'] === $logon_details['password']) {
            $valid_login = true;
        }
    }
    
   // check the user login
    if ($valid_login) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";
        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid Login!";
        die(json_encode($response));
    }

?> 

