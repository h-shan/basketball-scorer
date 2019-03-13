package com.shan.howard.balltracker.datamodels;

import java.util.Date;
import java.util.List;

public class Game {
    private String id;
    private Date date;
    private String yourTeamId;
    private String opponentTeamId;
    private String location;
    private int yourTeamScore;
    private int opposingTeamScore;
    private List<String> q1EventIds;
    private List<String> q2EventIds;
    private List<String> q3EventIds;
    private List<String> q4EventIds;
    private List<String> overtimeEventIds;

}
