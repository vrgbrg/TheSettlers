import flow.Game;
import flow.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    Game game = null;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGetTownTable() {
        Assert.assertEquals(game.getTable()[10][10], game.getTable()[10][10]);
        Assert.assertEquals(game.getTable()[35][5], game.getTable()[35][5]);
    }

}
