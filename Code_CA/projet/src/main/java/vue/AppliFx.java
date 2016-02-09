package vue;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceCarnetAdresse;

public class AppliFx extends Application{

	private ServiceCarnetAdresse service = new ServiceCarnetAdresse();

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws IOException  {
		Parent root = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
		Scene scene=new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}

