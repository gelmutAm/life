package com.epam.life.common;

import com.epam.life.logic.ColonyFieldLogicImpl;
import com.epam.life.logic.ColonyFieldLogic;

public class DependencyResolver {
    private static ColonyFieldLogic colonyFieldLogic;

    public static ColonyFieldLogic getColonyFieldLogic() {
        if (colonyFieldLogic == null) {
            colonyFieldLogic = new ColonyFieldLogicImpl(GameConfig.getInstance().getColumnQty(),
                    GameConfig.getInstance().getRowQty());
        }

        return colonyFieldLogic;
    }

    public static void setColonyFieldLogicToNull() {
        colonyFieldLogic = null;
    }
}