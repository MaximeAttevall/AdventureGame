/* 
 * För att köra manuella tester behöver man aktivera -ea flaggan-
 * för testklassen innan man kör annars fungerar inte assertions.
 */

public class Path5Test{

    static Path5 game;

    public static void setup_tests(){
        game = new Path5();
    }

    //Testar om assertions är aktiverade
    public static void AssertTest(){
        boolean assertsEnabled = false;
        try{
            assert false; //Assertions är inte aktiverade om vi kommer hit.
        }
        catch (AssertionError e){
            assertsEnabled = true; //Assertions är aktiverade om vi kommer hit.
        }

        if (assertsEnabled){
            System.out.println("Assertions är aktiverade\n");
        }
        else{
            System.out.println("Assertions är inte aktiverade\n");
        }
    }

    //Testar att bakgrunden laddats in vid initiering av user interface
    public static void test_initiateUI(){
        setup_tests();

        assert game.backgroundIMG != null : "Bakgrunden misslyckades att ladda in";
        System.out.println("test_P5BG_initiation\tgodkänd");
    }

    //Testar att knappar initieras
    public static void test_addButtons(){
        setup_tests();
        
        assert game.buttons[2] != null : "Knappar misslyckades att ladda in";
        System.out.println("test_P5buttons\t\t\tgodkänd");
    }

    //Testar att restart knapp initieras
    public static void test_createRestartBtn(){
        setup_tests();
        
        assert game.restartBtn != null : "Omstartknapp misslyckades att ladda in";
        System.out.println("test_createRestartBtn\tgodkänd");
    }

    //Testar om restart kan köras
    public static void test_restart(){
        setup_tests();

        game.restart();
        assert game.restartCount == 1 : "Omstart misslyckades";
        System.out.println("test_restart\t\t\tgodkänd");
    }

    //Testar om scen uppdateras efter en gamestate-ändring
    public static void test_updateScene(){
        setup_tests();

        game.gameState = 2;
        game.updateScene();

        assert game.description.getText().contains("monster") : "Uppdatering av scen misslyckades";
        System.out.println("test_updateScene\t\tgodkänd");
    }

    //Testar om updateBG kan köras
    public static void test_updateBG(){
        setup_tests();

        game.updateBG("Images/win.png");
        assert game.bgUpdateCount == 1 : "updatering av bakgrund misslyckades";
        System.out.println("test_updateBG\t\t\tgodkänd");
    }

    //Tester körs
    public static void main(String[] args){
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        AssertTest();
        test_initiateUI();
        test_addButtons();
        test_createRestartBtn();
        test_restart();
        test_updateScene();
        test_updateBG();

        System.out.println("\nAlla tester avklarade");
    }
}