package mhyhre.lightrabbit.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.StringsBase;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class ScenePauseGame extends EaseScene {

    private Text labelPause;
    private Text buttonTextResume;
    private Text buttonTextExit;

    public ScenePauseGame() {

        setBackgroundEnabled(false);

        String textPause = StringsBase.getInstance().getValue("Pause");
        String textResume = StringsBase.getInstance().getValue("Resume");
        String textExit = StringsBase.getInstance().getValue("Pause_Exit");


        Rectangle backRect = new Rectangle(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),
                MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.Me.getVertexBufferObjectManager());
        backRect.setColor(0.0f, 0.0f, 0.1f, 0.75f);
        attachChild(backRect);

        // Creating sprite
        Sprite buttonExitRect = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().mSceneGame.endGame();
                    MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                }
                return true;
            }
        };
        buttonExitRect.setScale(MainActivity.PIXEL_MULTIPLIER);

        Sprite buttonResumeRect  = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.Game);
                }
                return true;
            }
        };
        buttonResumeRect.setScale(MainActivity.PIXEL_MULTIPLIER);

        labelPause = new Text(0, 0, MainActivity.resources.getFont("White Furore"), textPause, MainActivity.Me.getVertexBufferObjectManager());
        labelPause.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight()*0.7f);

        buttonExitRect.setColor(1.0f, 1.0f, 1.0f);
        buttonResumeRect.setColor(1.0f, 1.0f, 1.0f);

        buttonResumeRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        buttonExitRect.setPosition(MainActivity.getHalfWidth(),  MainActivity.getHalfHeight() - 120);

        attachChild(buttonExitRect);
        attachChild(buttonResumeRect);
        registerTouchArea(buttonResumeRect);
        registerTouchArea(buttonExitRect);

        buttonTextResume = new Text(0, 0, MainActivity.resources.getFont("Furore"), textResume, MainActivity.Me.getVertexBufferObjectManager());
        buttonTextExit = new Text(0, 0, MainActivity.resources.getFont("Furore"), textExit, MainActivity.Me.getVertexBufferObjectManager());

        buttonTextResume.setPosition(buttonResumeRect);
        buttonTextExit.setPosition(buttonExitRect);

        attachChild(labelPause);
        attachChild(buttonTextResume);
        attachChild(buttonTextExit);

   }

    @Override
    public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }
}
