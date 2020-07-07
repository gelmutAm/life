package com.epam.common;

import com.epam.logic.ColonyFieldLogic;
import com.epam.logic.ColonyFieldLogicInterface;

public class DependencyResolver {
    private static ColonyFieldLogicInterface colonyFieldLogic;

    public static ColonyFieldLogicInterface getColonyFieldLogic(int m, int n) {
        if(colonyFieldLogic == null) {
            colonyFieldLogic = new ColonyFieldLogic(m, n);
        }

        return colonyFieldLogic;
    }
}
