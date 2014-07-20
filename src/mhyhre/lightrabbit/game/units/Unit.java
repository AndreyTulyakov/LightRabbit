package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.units.views.UnitView;

public class Unit {

    private UnitModel model;
    private UnitController controller;
    private UnitView view;
    
    public Unit(UnitModel model, UnitController controller, UnitView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
    }
    
    public void update() {
        controller.update();
        model.update();
        view.update();
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
