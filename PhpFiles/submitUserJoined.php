<?php



   //  here we check if they are duplicates in the database 
 // check all username is unique 
require("configuration.php");

    $query        = " SELECT 1 FROM userJoined WHERE group_id = :gId";
    
    $query_params = array(
        ':gId' => $_POST['group_id']
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
    
    /*
    $invalid_username = $stmt->fetch();
    if ($invalid_username) {
       
        $response["success"] = 0;
        $response["message"] = "Event name is already in use, please try again!";
        die(json_encode($response));
    }
*/
    
    // enter the values inside the database 
    $query = "INSERT INTO userJoined ( group_id ,user_id ) VALUES ( :gId,:uId ) ";
    
    
    $query_params = array(
        ':uId' => $_POST['user_id'],
        ':gId' => $_POST['group_id']
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
    $response["message"] = "User Successfully Added!";

// echo out the json data for andoird application to read
    echo json_encode($response);
?>
