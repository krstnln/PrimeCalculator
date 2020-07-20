package PrimeServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application{

	 //io streams
	 DataOutputStream toServer = null;
	 DataInputStream fromServer = null;

	 @Override
	 public void start(Stage stage) throws IOException {

		 //label, textField, button for user to enter number
	     Label inputLabel = new Label("Enter a number to check prime:");
	     TextField inputText = new TextField();
	     Button enter = new Button("Enter");

	     //scrollPane to hold results
	     BorderPane resultPane = new BorderPane();
	     TextArea ta = new TextArea();
	     resultPane.setCenter(new ScrollPane(ta));

	     //gridPane to hold labels and results
	     GridPane gridPane = new GridPane();
	     gridPane.setMinSize(400, 75);
	     gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
	     gridPane.setVgap(10);
	     gridPane.setHgap(10);
	     gridPane.setAlignment(Pos.TOP_CENTER);
	     gridPane.add(inputLabel, 0, 1);
	     gridPane.add(inputText, 1, 1);
	     gridPane.add(enter, 2, 1);
	     gridPane.add(resultPane, 0, 2, 3, 1);

	     //scene and stage to complete UI
	     Scene scene = new Scene(gridPane);
	     stage.setTitle("Client");
	     stage.setScene(scene);
	     stage.show();

	     //on action, client sends user input to server
	     enter.setOnAction(enterac ->
	     {
	    	 try {
				//convert input and send to server
				 int num = Integer.parseInt(inputText.getText());
				 toServer.writeInt(num);
				 toServer.flush();

				 //get answer from server
				 boolean flag = fromServer.readBoolean();

				 //display text
				 ta.appendText("Number entered: " + num + "\n");
				 if(flag == true)
					 ta.appendText(num + " is not a prime number.\n");
				 else
				     ta.appendText(num + " is a prime number.\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

	     });

    	 try {
    		//create a socket to connect to server
			Socket clientSocket = new Socket("localhost", 4999);

			//create input/output streams to send
			//data back and forth with the sever
			fromServer = new DataInputStream(clientSocket.getInputStream());
			toServer = new DataOutputStream(clientSocket.getOutputStream());
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	 }

	 public static void main(String[] args) {
		 launch(args);
	 }
}





