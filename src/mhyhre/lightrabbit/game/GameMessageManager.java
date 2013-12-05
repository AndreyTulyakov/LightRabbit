package mhyhre.lightrabbit.game;

import org.andengine.entity.text.Text;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.game.Levels.CharacterBase;
import mhyhre.lightrabbit.game.Levels.Dialog;
import mhyhre.lightrabbit.game.Levels.DialogBase;
import mhyhre.lightrabbit.game.Levels.Replic;
import mhyhre.lightrabbit.game.Levels.Character;;

public class GameMessageManager extends MhyhreScene {
    
    // TODO: add a fast message support
    // TODO: add show actor of replica

    private Text textMessage;
    private Text textCharacter;
    
    private Rectangle clickRect;
    private DialogBase dialogBase;
    private CharacterBase characterBase;
    
    private Dialog currentDialog;
    private Replic currentReplic;

    int lastMessageId = -1;
    boolean activeMessage = false;

    public GameMessageManager() {
        setBackgroundEnabled(false);

        clickRect = new Rectangle(0, 0, MainActivity.getWidth() - 200, MainActivity.getHeight() - 200, MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    // if has not next replic
                    if(currentReplic.getNextId() == 0) {
                        if(activeMessage) {
                            hide();
                            setIgnoreUpdate(true);
                            activeMessage = false;
                        }
                    } else {
                        currentReplic = currentDialog.getReplic(currentReplic.getNextId());
                        updateVisualMessage();
                    }
                    
                    Log.i(MainActivity.DEBUG_ID, "GameMessageManager: clickRect");
                    MainActivity.vibrate(30);
                }
                return true;
            }
        };
        clickRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        clickRect.setColor(0.9f, 0.9f, 1.0f, 0.5f);
        registerTouchArea(clickRect);

        textMessage = new Text(100, 100, MainActivity.resources.getFont("Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
        textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        
        textCharacter = new Text(100, 100, MainActivity.resources.getFont("White Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
        textCharacter.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight() + 100);
        
        attachChild(clickRect);
        attachChild(textMessage);
        attachChild(textCharacter);
    }

    private void updateVisualMessage() {
        textMessage.setText(currentReplic.getText());
        
        Character character = characterBase.getCharacter(currentReplic.getActorId());
        if(character != null) {
            textCharacter.setText(character.getName());
        }

        
    }
    
    public int lastShownMessage(){
        return lastMessageId;
    }

    public boolean isActiveMessage() {
        return activeMessage;
    }
    
    public void showMessage(int messageId) {
        lastMessageId = messageId;
        
        if(lastMessageId == -1) {
            activeMessage = false;
            hide();
            return;
        }

        if(dialogBase == null) {
            return;
        }
        
        currentDialog = dialogBase.getDialogs(lastMessageId);
        if(currentDialog == null) {
            showMessage(-1);
            return;
        }
        
        currentReplic = currentDialog.getFirstReplic();
        
        show();
        activeMessage = true;
        updateVisualMessage();
    }
    
    public void setDialogBase(DialogBase dialogBase) {
        this.dialogBase = dialogBase;
    }
    
    public void setCharacterBase(CharacterBase characterBase) {
        this.characterBase = characterBase;
    }

}
