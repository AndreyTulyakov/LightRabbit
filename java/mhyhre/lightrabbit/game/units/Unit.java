/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.units.views.UnitView;

public class Unit {

    private final UnitModel model;
    private final UnitController controller;
    private final UnitView view;
    
    public Unit(UnitModel model, UnitController controller, UnitView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        
        controller.setModel(getModel());
    }

    public UnitModel getModel() {
        return model;
    }

    public UnitController getController() {
        return controller;
    }

    public UnitView getView() {
        return view;
    }
}
