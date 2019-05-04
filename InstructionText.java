import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InstructionText {

   Font font;

   public InstructionText(){
	   font = Font.font("SansSerif", FontWeight.BOLD, 24);
   }

   public void render(GraphicsContext gc){
	   gc.setFill(Color.WHITE);
	   gc.setFont(font);
	   gc.fillText("Press Z for left\nPress X for right\nPress Q for restart", 600, 25);
	   
	   
   }

   

}