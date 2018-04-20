
public class Team {
  String teamName;
  Integer seed;

  public Team(String team, Integer seed) {
    this.teamName = team;
    this.seed = seed;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Integer getSeed() {
    return seed;
  }

  public void setSeed(Integer seed) {
    this.seed = seed;
  }
  
}
