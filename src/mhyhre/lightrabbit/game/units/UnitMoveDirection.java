package mhyhre.lightrabbit.game.units;

public enum UnitMoveDirection {
    NONE(0), LEFT(-1), RIGHT(1);
    
    private int direct;
    
    private UnitMoveDirection(int direct) {
        this.direct = direct;
    }
    
    public int getDirect() {
        return direct;
    }

}
