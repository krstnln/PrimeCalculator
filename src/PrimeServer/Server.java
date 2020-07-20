package PrimeServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Server extends Application {

	public void start(Stage stage) throws IOException {

	    //scrollPane to hold results
	    BorderPane resultPane = new BorderPane();
	    TextArea ta = new TextArea();
	    resultPane.setCenter(new ScrollPane(ta));

	    //gridPane to hold labels and results
	    GridPane gridPane = new GridPane();
	    gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
	    gridPane.setVgap(10);
	    gridPane.setHgap(10);
	    gridPane.setAlignment(Pos.TOP_CENTER);
	    gridPane.add(resultPane, 0, 0);

	    //scene and stage to complete UI
	    Scene scene = new Scene(gridPane);
	    stage.setTitle("Server");
	    stage.setScene(scene);
	    stage.show();

	    new Thread( () -> {
	    	try {
	    		//create a server socket
				ServerSocket serverSocket = new ServerSocket(4999);

				//accept data from client
			    Socket socket = serverSocket.accept();

			    //create input and output streams
			    DataInputStream clientInput = new DataInputStream(socket.getInputStream());
			    DataOutputStream serverOutput = new DataOutputStream(socket.getOutputStream());

			    //convert DataInputStream to int
			    while (true) {
				    int num = clientInput.readInt();

				    //compute if prime
				    boolean flag = false;
				    for(int i = 2; i <= num/2; ++i){
				    	if(num % i == 0){
				    		flag = true;
				    		break;
				    	}
				    }

				    //send answer to client
				    serverOutput.writeBoolean(flag);

					//output to server
					ta.appendText("The number received from client: " + num + '\n');
					if(flag == true)
						ta.appendText(num + " is not prime.\n");
					else
					    ta.appendText(num + " is prime.\n");
			    }
		    } catch(IOException ex) {
		        ex.printStackTrace();
		    }
	    }).start();
	}

	public static void main(String[] args) {
		 launch(args);
	}

}

