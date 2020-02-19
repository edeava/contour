package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import contour.Line;
import contour.Solver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Direction;
import model.Edge;
import model.Rectangle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebHistory;


public class Main extends Application {
	
	private int xB, yB, xE, yE, num;
	private Rectangle r = null;
	
	@Override
	public void start(Stage stage) {
		num = 1;
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		try {
			int input;
			Scanner sc = new Scanner(System.in);
			input = sc.nextInt();
			
			stage.setTitle("Contour"); 
			Button btn = new Button("Solve");
			
	        Canvas canvas = new Canvas(800.0f, 800.0f); 
	        Canvas c1 = new Canvas(800.0f, 800.0f); 
	   
	        GraphicsContext gc = canvas.getGraphicsContext2D(); 
	        GraphicsContext gc1 = c1.getGraphicsContext2D(); 
	  
	        //Group group = new Group(canvas);
	        GridPane gp = new GridPane();
	        gp.add(btn, 1, 0);
	        gp.add(canvas, 0, 0);
	        Group g1 = new Group(c1);

	        Scene scene = new Scene(gp, 1000, 800); 
	        Scene scene1 = new Scene(g1, 800, 800);
	        
	        File file = new File("input.txt"); 
			  
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			stage.setScene(scene);
			stage.show(); 
			  
			String st; 
			int stop = 0;
			if(input == 0) {
				while ((st = br.readLine()) != null){
					String s[] = st.split(" ");
					Rectangle.map.put(num, new Rectangle(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])));
					Solver.slabs.add(new Edge(num, Direction.UP));
		            Solver.slabs.add(new Edge(num, Direction.DOWN));
		            num++;
					
					int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[3]);
			        gc.moveTo(x, y);
			        x = Integer.parseInt(s[0]); y = Integer.parseInt(s[2]);
			        gc.lineTo(x, y);
		            gc.stroke();
		            x = Integer.parseInt(s[1]); y = Integer.parseInt(s[2]);
			        gc.lineTo(x, y);
		            gc.stroke();
		            x = Integer.parseInt(s[1]); y = Integer.parseInt(s[3]);
			        gc.lineTo(x, y);
		            gc.stroke();
		            x = Integer.parseInt(s[0]); y = Integer.parseInt(s[3]);
			        gc.lineTo(x, y);
		            gc.stroke();
		            
				}
			}else {
					canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
			                new EventHandler<MouseEvent>(){
	
			            @Override
			            public void handle(MouseEvent event) {
			                xB = (int) event.getX();
			                yB = (int) event.getY();
			                gc.moveTo(event.getX(), event.getY());
			                gc.stroke();
			            }
			        });
	
			        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
			                new EventHandler<MouseEvent>(){
	
			            @Override
			            public void handle(MouseEvent event) {
			               
			            }
			        });
	
			        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
			                new EventHandler<MouseEvent>(){
	
			            @Override
			            public void handle(MouseEvent event) {
			            	xE = (int) event.getX();
			                yE = (int) event.getY();
			                r = new Rectangle(Integer.min(xB, xE), Integer.max(xB, xE), Integer.max(yB, yE), Integer.min(yB, yE));
			                Rectangle.map.put(num, r);
			                Solver.slabs.add(new Edge(num, Direction.UP));
				            Solver.slabs.add(new Edge(num, Direction.DOWN));
				            num++;
			                if(r != null) {
						        gc.moveTo(r.getLeftX(), r.getLeftY());
						        gc.lineTo(r.getLeftX(), r.getRightY());
					            gc.stroke();
					            gc.lineTo(r.getRightX(), r.getRightY());
					            gc.stroke();
					            gc.lineTo(r.getRightX(), r.getLeftY());
					            gc.stroke();
					            gc.lineTo(r.getLeftX(), r.getLeftY());
					            gc.stroke();	
					        }
			            }
			        });
			}
	       
	        btn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					Stage s1 = new Stage();
					s1.setTitle("Solver");
					Canvas c1 = new Canvas(800.0f, 800.0f); 
			        GraphicsContext gc1 = c1.getGraphicsContext2D(); 
			        Group g1 = new Group(c1);
			        Scene scene1 = new Scene(g1, 800, 800);
			        s1.setScene(scene1);
					Solver.solve();
					s1.show();
					Timeline timeline = new Timeline();
					double step = 0.5;
					double d = 0.5;
					for (Iterator iterator = Solver.cont.iterator(); iterator.hasNext();) {
						Line l = (Line) iterator.next();
		
			            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d), e -> draw(gc1, l)));
			            d += step;
					}
					System.out.println(timeline.getKeyFrames().size());
					timeline.play();
					
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void draw(GraphicsContext gc1, Line l) {
		gc1.moveTo(l.getX1(), l.getY1());
	    gc1.lineTo(l.getX2(), l.getY2());
        gc1.stroke();
	}
}
