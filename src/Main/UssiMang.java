package Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UssiMang extends Application {
	
	public enum Direction { 
		YLES, ALLA, VASAKULE, PAREMALE
	}
	
	int ruuduSuurus = 80;
	int aknaLaius = ruuduSuurus * 10;
	int aknaKorgus = ruuduSuurus * 10;

	Direction suund = Direction.ALLA; 
	boolean ussLiikus = false; 
	
	Timeline animatsioon = new Timeline(); 
	
	ObservableList<Node> uss;  
	
	
	private Parent manguSisu() { 
		
		Pane aken = new Pane(); 
		aken.setPrefSize(aknaLaius, aknaKorgus);
		
		Group ussiKeha = new Group(); 
		uss = ussiKeha.getChildren(); 
		
		Rectangle toit = new Rectangle(ruuduSuurus, ruuduSuurus);
		toit.setFill(Color.YELLOWGREEN);
		toit.setTranslateX((int)(Math.random() * aknaLaius) / ruuduSuurus * ruuduSuurus); 
		toit.setTranslateY((int)(Math.random() * aknaKorgus) / ruuduSuurus * ruuduSuurus);
		
		KeyFrame kaader = new KeyFrame(Duration.seconds(0.2), e -> { 
			
			boolean eemaldadaSaba = uss.size() > 1; 
			
			Node ussiPea = eemaldadaSaba ? uss.remove(uss.size()-1) : uss.get(0); 
			
			double vanaX = ussiPea.getTranslateX(); 
			double vanaY = ussiPea.getTranslateY();
			
			
			switch (suund) { 
            	case YLES:
            		ussiPea.setTranslateX(uss.get(0).getTranslateX()); 
            		ussiPea.setTranslateY(uss.get(0).getTranslateY() - ruuduSuurus); 
            		break;
            	case ALLA:
            		ussiPea.setTranslateX(uss.get(0).getTranslateX());
            		ussiPea.setTranslateY(uss.get(0).getTranslateY() + ruuduSuurus);
                	break;
            	case VASAKULE:
            		ussiPea.setTranslateX(uss.get(0).getTranslateX() - ruuduSuurus);
            		ussiPea.setTranslateY(uss.get(0).getTranslateY());
            		break;
            	case PAREMALE:
            		ussiPea.setTranslateX(uss.get(0).getTranslateX() + ruuduSuurus);
            		ussiPea.setTranslateY(uss.get(0).getTranslateY());
            		break;
			}
			
			ussLiikus = true; 
			
			if (eemaldadaSaba) 
				uss.add(0, ussiPea); 
		
			// Uss läbi raami
			if (ussiPea.getTranslateX() < 0) {
				ussiPea.setTranslateX(aknaLaius - ruuduSuurus);
			}
			
			if (ussiPea.getTranslateX() >= aknaLaius) {
				ussiPea.setTranslateX(0);
			}
			
			if (ussiPea.getTranslateY() < 0) {
				ussiPea.setTranslateY(aknaKorgus - ruuduSuurus);
			}
			
			if (ussiPea.getTranslateY() >= aknaKorgus) {
				ussiPea.setTranslateY(0);
			}
			
			
			for (Node ruut : uss) {  
				if(ruut != ussiPea && ussiPea.getTranslateX() == ruut.getTranslateX() && ussiPea.getTranslateY() == ruut.getTranslateY()) {
					restartGame(); 
					break;
				}
			}
			
			
			if (ussiPea.getTranslateX() == toit.getTranslateX() && ussiPea.getTranslateY() == toit.getTranslateY()) { 
				toit.setTranslateX((int)(Math.random() * aknaLaius) / ruuduSuurus * ruuduSuurus); 
				toit.setTranslateY((int)(Math.random() * aknaKorgus) / ruuduSuurus * ruuduSuurus);
				
				Rectangle uusPea = new Rectangle(ruuduSuurus, ruuduSuurus);
				uusPea.setFill(Color.DARKGREEN);
				uusPea.setTranslateX(vanaX);
				uusPea.setTranslateY(vanaY);
				uss.add(uusPea); 
			}
			
		});
		
		animatsioon.getKeyFrames().add(kaader); 
		animatsioon.setCycleCount(Timeline.INDEFINITE); 
		
		aken.getChildren().addAll(toit, ussiKeha); 
		return aken;
	
	}
	
	
	private void restartGame () { 
		lopetaMang();
		alustaMangu();
		
	}

	private void alustaMangu() {
		suund = Direction.ALLA;
		Rectangle uusUss = new Rectangle(ruuduSuurus, ruuduSuurus);
		uusUss.setFill(Color.DARKGREEN);
		uss.add(uusUss); 
		animatsioon.play();
		
	}
	
	private void lopetaMang() {
		animatsioon.stop(); 
		uss.clear(); 
		
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override 
	public void start(Stage primaryStage) throws Exception {
		Scene stseen = new Scene(manguSisu());
		
		// Sisendi käitlemine.
		stseen.setOnKeyPressed(e -> {
			if (!ussLiikus)
				return;
			
			
			switch (e.getCode()) { 
					case UP:
						if (suund != Direction.ALLA) 
							suund = Direction.YLES; 
						break; 
					case W:
						if (suund != Direction.ALLA) 
							suund = Direction.YLES; 
						break;	
					case DOWN:
						if (suund != Direction.YLES)
							suund = Direction.ALLA;
						break;
					case S:
						if (suund != Direction.YLES)
							suund = Direction.ALLA;
						break;
					case LEFT:
						if (suund != Direction.PAREMALE)
							suund = Direction.VASAKULE;
						break;
					case A:
						if (suund != Direction.PAREMALE)
							suund = Direction.VASAKULE;
						break;
					case RIGHT:
						if (suund != Direction.VASAKULE)
							suund = Direction.PAREMALE;
						break;
					case D:
						if (suund != Direction.VASAKULE)
							suund = Direction.PAREMALE;
						break;
					default:
						break;
			}
			
			ussLiikus = false; 
			
		});
		
		primaryStage.setScene(stseen);
		primaryStage.setTitle("U S S I M Ä N G");
		stseen.setFill(Color.LIGHTCYAN);
		primaryStage.show();
		alustaMangu();
		
	
	}

}

