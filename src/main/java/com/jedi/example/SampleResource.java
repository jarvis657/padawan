package com.jedi.example;

import com.jedi.example.padawan.core.annotations.JedisShards;
import com.jedi.example.padawan.core.redis.JedisShardedPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Path("/source")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SampleResource {

    @JedisShards
    private JedisShardedPool pool;

    public static final String mapKey = "map";

    @POST
    @Path("/put")
    public void putInMaster(){
        Jedis conn = pool.getWriteResource();
        conn.set(mapKey, "Setting map key value");
        pool.returnWriteResource(conn);
    }

    @GET
    @Path("/get")
    public String getFromMaster(){
        Jedis conn = pool.getWriteResource();
        String val =conn.get(mapKey);
        pool.returnWriteResource(conn);
        return val;
    }

    @GET
    @Path("/getshard")
    public String getFromShards(){
        ShardedJedis conn = pool.getReadResource();
        String val = conn.get(mapKey);
        pool.returnReadResource(conn);
        return val;
    }
}
