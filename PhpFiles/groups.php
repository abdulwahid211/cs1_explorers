<?php

/*
error_reporting(E_ALL ^ E_DEPRECATED); // show errors 
$username = "wadz"; 
$password = "abzy211"; 
$host = "localhost"; 
$dbname = "test"; 
$dbTable ="group";

*/


   //  here we check if they are duplicates in the database 
 // check all username is unique 
require("configuration.php");

    $query        = " SELECT 1 FROM userGroups WHERE eventName = :event";
    
    $query_params = array(
        ':event' => $_POST['eventName']
    );
   
    
    try {
        // run the query in our database 
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        
        
      // if there is a databse error, kill the json code 
        $response["success"] = 0;
        $response["message"] = "Database Error dude. Please Try Again!";
        die(json_encode($response));
    }
    
    
    $invalid_username = $stmt->fetch();
    if ($invalid_username) {
       
        $response["success"] = 0;
        $response["message"] = "Event name is already in use, please try again!";
        die(json_encode($response));
    }
    
    // enter the values inside the database 
    $query = "INSERT INTO userGroups ( admin_Id ,eventName, location, time, description, ageGroup ,postCode, language) VALUES ( :adminId,:event, :loc,:tim,:des,:age,:pos,:lan ) ";
    
    
    $query_params = array(
        ':adminId' => $_POST['admin_id'],
        ':event' => $_POST['eventName'],
        ':loc' => $_POST['location'],
        ':tim' => $_POST['time'],
        ':des' => $_POST['description'],
        ':age' => $_POST['ageGroup'],
		':pos' => $_POST['postCode'],
		':lan' => $_POST['language']
    );
    
   // execute the query 
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
// query request has failed 
    catch (PDOException $ex) {
       
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));
    }
    // display the sucessfull login message 
    $response["success"] = 1;
    $response["message"] = "Group Successfully Created!";

// echo out the json data for andoird application to read
    echo json_encode($response);
?>
