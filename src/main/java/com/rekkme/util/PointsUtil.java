package com.rekkme.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class PointsUtil {

    @Value("${app.api.lowPoints:100}")
    private static Integer lowPoints;
    
    public static Integer calculatePoints(int currentPoints, int wageredPoints, int resultPoints,
        boolean ko) {
        
        // they got a ko so double it up
        if (ko == true) {
            return currentPoints + (wageredPoints * 2);
        }

        // the got higher than the wager
        if (wageredPoints <= resultPoints) {
            return currentPoints + (wageredPoints + ((resultPoints - wageredPoints) / 10));
        }

        // they get revived
        // TO-DO send notification about the revival
        if (currentPoints == 0) {
            return lowPoints;
        }

        // penalize less
        if (currentPoints <= lowPoints) {
            return currentPoints - (wageredPoints / 2);
        }

        // if this will give them 0 points give them a save
        // TO-DO send notification about the revival
        if (currentPoints - wageredPoints == 0) {
            return lowPoints;
        }

        // normal loss
        return currentPoints - wageredPoints;
    }
}
