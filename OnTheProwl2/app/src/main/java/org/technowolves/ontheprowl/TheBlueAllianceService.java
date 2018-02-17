package org.technowolves.ontheprowl;

import org.technowolves.ontheprowl.model.District;
import org.technowolves.ontheprowl.model.Event;
import org.technowolves.ontheprowl.model.team.Team;

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

    @GET("/district/{year}")
    Call<List<District>> listDistricts(@Path("year") String year,
                                       @Header("X-TBA-Auth-Key") String authKey);

    @GET("district/{district}/events/simple")
    Call<List<Event>> listEvents(@Path("district") String districtKey,
                                 @Header("X-TBA-Auth-Key") String authKey);

    @GET("event/{event}/teams")
    Call<List<Team>> listTeams(@Path("event") String eventKey,
                               @Header("X-TBA-Auth-Key") String authKey);

}
