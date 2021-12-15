import com.example.aventurasdemarcoyluis.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class phasesTest {
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	private GameController c;

	@BeforeEach
	public void setUp() {
		c = new GameController();
		c.setSeed(123456789); //Special seed with 3 different enemies at the beginning
		c.emptyChest();

		c.setErrorStream(new PrintStream(outputStreamCaptor));
	}
}
