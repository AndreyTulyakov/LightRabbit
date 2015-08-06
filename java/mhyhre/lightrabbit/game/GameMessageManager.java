/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import android.util.Log;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.Character;
import mhyhre.lightrabbit.game.levels.CharacterBase;
import mhyhre.lightrabbit.game.levels.Dialog;
import mhyhre.lightrabbit.game.levels.DialogBase;
import mhyhre.lightrabbit.game.levels.Replic;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class GameMessageManager extends EaseScene {

    public enum ShowMode {
        SHOW_DIALOGS, SHOW_TITLES, SHOW_REPLIC;
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
    private int replicTime = 1;


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

                    if (currentShowMode == ShowMode.SHOW_DIALOGS) {
                        if (currentReplic.getNextId() == 0) {
                            if (activeMessage) {
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

                    if (currentShowMode == ShowMode.SHOW_DIALOGS) {
                        // if has not next replic
                        if (currentReplic.getNextId() == 0) {
                            if (activeMessage) {
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
        textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() * 0.43f);
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

        Character character;
        Log.w(MainActivity.DEBUG_ID, "updateVisualMessage" + currentShowMode.name());

        switch (currentShowMode) {

            case SHOW_DIALOGS:
                clickRect.setVisible(true);

                actorRect.setVisible(true);
                textTitle.setVisible(false);
                titleBackgroundRect.setVisible(false);
                textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() * 0.43f);
                textMessage.setText(currentReplic.getText());
                textMessage.setAlpha(0);
                textMessage.setVisible(true);

                character = characterBase.getCharacter(currentReplic.getActorId());
                if (character == null) {
                    Log.w(MainActivity.DEBUG_ID, "character == null");
                    return;
                }

                textCharacter.setVisible(true);
                textCharacter.setText(character.getName());
                Log.w(MainActivity.DEBUG_ID, "Char name:" + character.getName());


                if (characterIcon != null) {
                    characterIcon.detachSelf();
                    characterIcon = null;
                }

                if (character.getIconRegion() != null) {
                    characterIcon = new Sprite(128, MainActivity.getHeight() * 0.75f, character.getIconRegion(), MainActivity.getVboManager());
                    characterIcon.setScale(8);
                    attachChild(characterIcon);
                }

                break;

            case SHOW_TITLES:

                textTitle.setVisible(true);
                clickRect.setVisible(false);
                actorRect.setVisible(false);
                textMessage.setVisible(false);
                textCharacter.setVisible(false);
                titleBackgroundRect.setVisible(true);
                if (characterIcon != null) {
                    characterIcon.detachSelf();
                    characterIcon = null;
                }
                textTitle.setText(currentReplic.getText());
                textTitle.setAlpha(0);
                break;

            case SHOW_REPLIC:
                textTitle.setVisible(false);
                clickRect.setVisible(false);
                actorRect.setVisible(false);
                textMessage.setVisible(false);
                textCharacter.setVisible(false);
                titleBackgroundRect.setVisible(false);

                textMessage.setVisible(true);
                textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() * 0.20f);
                textMessage.setText(currentReplic.getText());
                textMessage.setAlpha(0);

                character = characterBase.getCharacter(currentReplic.getActorId());
                if (character == null) {
                    return;
                }

                if (characterIcon != null) {
                    characterIcon.detachSelf();
                    characterIcon = null;
                }
                break;


            default:
                // None
                break;

        }

    }

    public int lastShownMessage() {
        return lastMessageId;
    }

    public boolean isActiveMessage() {
        return activeMessage;
    }

    public void showMessage(int messageId) {

        clickRect.setVisible(true);
        clickRect.setIgnoreUpdate(false);

        lastMessageId = messageId;

        if (lastMessageId == -1) {
            activeMessage = false;
            hide();
            return;
        }

        if (dialogBase == null) {
            return;
        }

        currentDialog = dialogBase.getDialogs(lastMessageId);
        if (currentDialog == null) {
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

        if (activeMessage) {
            switch (currentShowMode) {

                case SHOW_DIALOGS:
                    textCharacter.setVisible(true);
                    if (textMessage.getAlpha() < 1.0f) {
                        textMessage.setAlpha(textMessage.getAlpha() + 0.05f);
                        if (textMessage.getAlpha() > 1.0f) {
                            textMessage.setAlpha(1.0f);
                        }
                    }
                    break;

                case SHOW_TITLES:

                    switch (currentTitlePeriod) {

                        case HIDE_TEXT:
                            if (textTitle.getAlpha() > 0.5f) {
                                textTitle.setAlpha(textTitle.getAlpha() - 0.033f);
                            } else {
                                textTitle.setAlpha(0.0f);
                                currentTitlePeriod = TitlePeriod.SHOW_TEXT;
                                titlesTime = 0;

                                if (currentReplic.getNextId() == 0) {
                                    if (activeMessage) {
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
                            if (titlesTime > TITLE_MESSAGE_SHOW_TIME) {
                                titlesTime = 0;
                                currentTitlePeriod = TitlePeriod.HIDE_TEXT;
                            }
                            break;

                        case SHOW_TEXT:
                            if (textTitle.getAlpha() < 0.95f) {
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
                    break;

                case SHOW_REPLIC:

                    switch (currentTitlePeriod) {

                        case HIDE_TEXT:
                            if (textMessage.getAlpha() > 0.12f) {
                                textMessage.setAlpha(textMessage.getAlpha() - 0.1f);
                            } else {
                                textMessage.setAlpha(0.0f);
                                currentTitlePeriod = TitlePeriod.SHOW_TEXT;
                                titlesTime = 0;

                                if (activeMessage) {
                                    hide();
                                    setIgnoreUpdate(true);
                                    activeMessage = false;
                                }
                            }
                            break;

                        case HOLD_TEXT:
                            if (titlesTime > replicTime) {
                                titlesTime = 0;
                                currentTitlePeriod = TitlePeriod.HIDE_TEXT;
                            }
                            break;

                        case SHOW_TEXT:
                            if (textMessage.getAlpha() < 0.90f) {
                                textMessage.setAlpha(textMessage.getAlpha() + 0.1f);
                            } else {
                                textMessage.setAlpha(1.0f);
                                currentTitlePeriod = TitlePeriod.HOLD_TEXT;
                                titlesTime = 0;
                            }
                            break;
                        default:
                            break;

                    }
                    break;

            }
        }

        super.onManagedUpdate(pSecondsElapsed);
    }

    public void showTitles(int messageId) {

        clickRect.setVisible(false);
        clickRect.setIgnoreUpdate(false);

        lastMessageId = messageId;

        if (lastMessageId == -1) {
            activeMessage = false;
            hide();
            return;
        }

        if (dialogBase == null) {
            return;
        }

        currentDialog = dialogBase.getDialogs(lastMessageId);
        if (currentDialog == null) {
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


    public void showReplic(int messageId, int timeout) {

        clickRect.setVisible(false);
        clickRect.setIgnoreUpdate(true);

        lastMessageId = messageId;
        replicTime = timeout;

        if (lastMessageId == -1) {
            activeMessage = false;
            hide();
            return;
        }

        if (dialogBase == null) {
            return;
        }

        currentDialog = dialogBase.getDialogs(lastMessageId);
        if (currentDialog == null) {
            showMessage(-1);
            return;
        }

        currentReplic = currentDialog.getFirstReplic();

        currentShowMode = ShowMode.SHOW_REPLIC;
        currentTitlePeriod = TitlePeriod.SHOW_TEXT;

        show();
        activeMessage = true;
        updateVisualMessage();
    }


    public ShowMode getCurrentShowMode() {
        return currentShowMode;
    }
}
