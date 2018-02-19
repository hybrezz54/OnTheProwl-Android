package com.hybrez.ontheprowl;

import com.hybrez.ontheprowl.model.District;
import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.model.team.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Interface to The Blue Alliance's API V3
 * Base url: https://www.thebluealliance.com/api/v3
 */
public interface TheBlueAllianceService {

    /**
     * Get a list of districts for a given year
     *
     * @param year The year of the season
     * @param authKey The TBA authentication key
     * @return A list of districts
     */
    @GET("districts/{year}")
    Call<List<District>> listDistricts(@Path("year") String year,
                                       @Header("X-TBA-Auth-Key") String authKey);

    /**
     * Get a list of events for a specified district
     *
     * @param districtKey The key of the district (e.g. 2017ncral)
     * @param authKey The TBA authentication key
     * @return
     */
    @GET("district/{district}/events/simple")
    Call<List<Event>> listEventsByDistrict(@Path("district") String districtKey,
                                 @Header("X-TBA-Auth-Key") String authKey);

    /**
     * Get a list of the events a team is attending for
     * a given season
     *
     * @param teamKey The key of the team (e.g. frc5518)
     * @param frcSeason The year of the season
     * @param authKey The TBA authentication Key
     * @return A list of events a team is attending
     */
    @GET("team/{team}/events/{year}/simple")
    Call<List<Event>> listEventsByTeam(@Path("team") String teamKey,
                                       @Path("year") String frcSeason,
                                       @Header("X-TBA-Auth-Key") String authKey);

    /**
     * Get a list of teams attending an event
     *
     * @param eventKey The key of the event
     * @param authKey The TBA authentication key
     * @return A list of teams
     */
    @GET("event/{event}/teams")
    Call<List<Team>> listTeams(@Path("event") String eventKey,
                               @Header("X-TBA-Auth-Key") String authKey);

}
