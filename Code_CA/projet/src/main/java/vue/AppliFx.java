package vue;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppliFx extends Application{



	public static void main(String[] args) {
		System.out.println( "Main method inside Thread : " +  Thread.currentThread().getName());
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setWidth(1024);
	        primaryStage.setHeight(968);
	        primaryStage.setTitle("JavaFX Xebia"); 
	        primaryStage.show();
	           
	}
}

