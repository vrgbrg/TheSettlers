import flow.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    Player player = null;

    @Before
    public void setUp() {
        player = new Player("Brigi");
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Brigi", player.getName());
    }

    @Test
    public void testChangeRoundPoints() {
        Assert.assertEquals(player.getRoundPoint() - 1, player.changeRoundPoint(1));
        Assert.assertNotEquals(player.getRoundPoint() + 1, player.changeRoundPoint(1));
    }

    @Test
    public void testGetTownTable() {
        Assert.assertEquals(player.getTownTable()[1][1], player.getTownTable()[1][1]);
        Assert.assertEquals(player.getTownTable()[5][9], player.getTownTable()[5][9]);
    }

}
