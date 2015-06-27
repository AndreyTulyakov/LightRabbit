/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.Character;
import mhyhre.lightrabbit.game.levels.CharacterBase;
import mhyhre.lightrabbit.game.levels.Dialog;
import mhyhre.lightrabbit.game.levels.DialogBase;
import mhyhre.lightrabbit.game.levels.Replic;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class GameMessageManager extends EaseScene {

    private enum ShowMode {
        SHOW_DIALOGS, SHOW_TITLES;
    }
    
    private enum TitlePeriod {
        SHOW_TEXT, HOLD_TEXT, HIDE_TEXT;
    }
    
    private Text textMessage;
    private Text textCharacter;
    private Text textTitle;
    private Sprite characterIcon = null;
    
    private Rectangle clickRect;
    private Rectangle actorRect;
    private Rectangle titleBackgroundRect;
    private DialogBase dialogBase;
    private CharacterBase characterBase;

    private Dialog currentDialog;
    private Replic currentReplic;

    private int lastMessageId = -1;
    private boolean activeMessage = false;
    
    private ShowMode currentShowMode = ShowMode.SHOW_DIALOGS;
    private double titlesTime = 0;
    private static final double TITLE_MESSAGE_SHOW_TIME = 2;
    private TitlePeriod currentTitlePeriod;
    
    public GameMessageManager() {
        
        setBackgroundEnabled(false);
        
        titleBackgroundRect = new Rectangle(
                MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),
                MainActivity.getWidth(), MainActivity.getHeight(),
                MainActivity.Me.getVertexBufferObjectManager());
        titleBackgroundRect.setColor(1.0f, 1.0f, 1.0f, 0.50f);
    
        textTitle = new Text(100, 100, MainActivity.resources.getFont("Furore"), "", 256, MainActivity.Me.getVertexBufferObjectManager());
        textTitle.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        textTitle.setHorizontalAlign(HorizontalAlign.CENTER);
        textTitle.setLeading(20);

        clickRect = new Rectangle(0, 0, MainActivity.getWidth() - 100, MainActivity.getHeight() * 0.66f, MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    if(currentShowMode == ShowMode.SHOW_DIALOGS) {
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
                    
                    
                }
                return true;
            }
        };
        
        clickRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        clickRect.setColor(0.0f, 0.0f, 0.0f, 0.60f);
        registerTouchArea(clickRect);
        
        
        
        actorRect = new Rectangle(0, 0, MainActivity.getWidth() - 60, 100, MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    if(currentShowMode == ShowMode.SHOW_DIALOGS) {
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
                   
                        MainActivity.vibrate(20);
                    }
                }
                return true;
            }
        };
        
        actorRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() * 0.75f);
        actorRect.setColor(0.0f, 0.0f, 0.0f, 0.40f);

        textMessage = new Text(100, 100, MainActivity.resources.getFont("White Sangha 28"), "", 256, MainActivity.Me.getVertexBufferObjectManager());
        textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight()*0.43f);
        textMessage.setHorizontalAlign(HorizontalAlign.CENTER);
        textMessage.setLeading(20);
        
        
        textCharacter = new Text(100, 100, MainActivity.resources.getFont("White Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
        textCharacter.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() * 0.75f);
        
        
        attachChild(titleBackgroundRect);
        attachChild(textTitle);
        attachChild(clickRect);
        attachChild(actorRect);
        attachChild(textMessage);
        attachChild(textCharacter);
    }

    private void updateVisualMessage() {
        
        switch(currentShowMode) {
        case SHOW_DIALOGS:
            clickRect.setVisible(true);
            actorRect.setVisible(true);
            textTitle.setVisible(false);
            titleBackgroundRect.setVisible(false);
            textMessage.setText(currentReplic.getText());
            textMessage.setAlpha(0);
            textMessage.setVisible(true);
            
            Character character = characterBase.getCharacter(currentReplic.getActorId());
            if(character == null) {
                return;
            }
            textCharacter.setAlpha(1);            
            textCharacter.setText(character.getName());

            
            if(characterIcon != null) {
                characterIcon.detachSelf();
                characterIcon = null;
            }
            
            if(character.getIconRegion() != null) {  
                characterIcon = new Sprite(128, MainActivity.getHeight() * 0.75f, character.getIconRegion(), MainActivity.getVboManager());
                characterIcon.setScale(2);
                attachChild(characterIcon);
            }
            break;
            
        case SHOW_TITLES:
            textTitle.setVisible(true);
            clickRect.setVisible(false);
            actorRect.setVisible(false);
            textMessage.setVisible(false);
            titleBackgroundRect.setVisible(true);

            textTitle.setText(currentReplic.getText());
            textTitle.setAlpha(0);
            textCharacter.setAlpha(0);        
            break;
            
        default:
            // None
            break;
        
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
        currentShowMode = ShowMode.SHOW_DIALOGS;
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
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        titlesTime += pSecondsElapsed;
        
        if(activeMessage) {
            if(currentShowMode == ShowMode.SHOW_DIALOGS) {
                if(textMessage.getAlpha() < 1.0f) {
                    textMessage.setAlpha(textMessage.getAlpha() + 0.05f);
                    if(textMessage.getAlpha() > 1.0f) {
                        textMessage.setAlpha(1.0f);
                    }
                }
            } else {
                switch(currentTitlePeriod) {
                
                case HIDE_TEXT:
                    if(textTitle.getAlpha() > 0.5f) {
                        textTitle.setAlpha(textTitle.getAlpha() - 0.033f);
                    } else {
                        textTitle.setAlpha(0.0f);
                        currentTitlePeriod = TitlePeriod.SHOW_TEXT;
                        titlesTime = 0;
                        
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
                        
                    }
                    break;
                    
                case HOLD_TEXT:
                    if(titlesTime > TITLE_MESSAGE_SHOW_TIME) {
                        titlesTime = 0;
                        currentTitlePeriod = TitlePeriod.HIDE_TEXT;
                    }
                    break;
                    
                case SHOW_TEXT:
                    if(textTitle.getAlpha() < 0.95f) {
                        textTitle.setAlpha(textTitle.getAlpha() + 0.033f);
                    } else {
                        textTitle.setAlpha(1.0f);
                        currentTitlePeriod = TitlePeriod.HOLD_TEXT;
                        titlesTime = 0;
                    }
                    break;
                default:
                    break;
                
                }
                
            }
        }
        
        super.onManagedUpdate(pSecondsElapsed);
    }

    public void showTitles(int messageId) {
        
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
        
        currentShowMode = ShowMode.SHOW_TITLES;
        currentTitlePeriod = TitlePeriod.SHOW_TEXT;
        
        show();
        activeMessage = true;
        updateVisualMessage();
    }


}
