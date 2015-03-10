<?php
/*

****   THIS IS PHP SCRIPT HAS BEEN ADOPTED FROM MY BRING BACK FROM YOUTUBE TUTORIALS ****

REFERENCE : 
https://www.youtube.com/watch?v=mQGQADq3iOQ


*/
//import and load the configuration settings so we can connect to our database
require("configuration.php");

   //  here we check if they are duplicates in the database 
 // check all username is unique 
    $query        = " SELECT 1 FROM userInfo WHERE username = :user";
    
    $query_params = array(
        ':user' => $_POST['username']
    );
   
    
    try {
        // run the query in our database 
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        
        
      // if there is a databse error, kill the json code 
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));
    }
    
    
    $invalid_username = $stmt->fetch();
    if ($invalid_username) {
       
        $response["success"] = 0;
        $response["message"] = " Username is already in use, please again!";
        die(json_encode($response));
    }
    
    // enter the values inside the database 
    $query = "INSERT INTO userInfo ( username, password,FirstName,LastName,Age,Nationality,Occupation,About ) VALUES ( :user, :pass,:fname,:lname,:age,:nat,:occ,:abo ) ";
    
    
    $query_params = array(
        ':user' => $_POST['username'],
        ':pass' => $_POST['password'],
        ':fname' => $_POST['FirstName'],
        ':lname' => $_POST['LastName'],
		':age' => $_POST['Age'],
        ':nat' => $_POST['Nationality'],
		':occ' => $_POST['Occupation'],
		':abo' => $_POST['About']
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
    $response["message"] = "Details Successfully Added!";

// echo out the json data for andoird application to read
    echo json_encode($response);
?>
