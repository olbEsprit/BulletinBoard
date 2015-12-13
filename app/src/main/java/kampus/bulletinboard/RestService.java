package kampus.bulletinboard;

/**
 * Created by Павел on 12.12.2015.
 */
public class RestService {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    private static final String URL = "http://jsonplaceholder.typicode.com/";
    private retrofit.RestAdapter restAdapter;
    private BulletinService apiService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(BulletinService.class);
    }

    public BulletinService getService()
    {
        return apiService;
    }
}