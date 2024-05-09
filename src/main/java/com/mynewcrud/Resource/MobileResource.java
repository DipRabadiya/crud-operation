package com.mynewcrud.Resource;

import java.util.ArrayList;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Path("/mobile")
//public class MobileResource {
//
//    List<String> mobileList = new ArrayList<>();
//
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response getMobileList(){
//        return Response.ok(mobileList).build();
//    }
//
//    @POST
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response addNewMobile(String mobileName){
//        mobileList.add(mobileName);
//        return Response.ok(mobileName).build();
//    }
//
//    @PUT
//    @Path("/{oldmobilename}")
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response updateMobile(@PathParam("oldmobilename") String oldMobileName,
//                                 @QueryParam("newmobilename") String newMobileName){
//        mobileList = mobileList.stream().map(mobile ->
//        {
//            if(mobile.equals(oldMobileName)){
//                return newMobileName;
//            }else {
//                return mobile;
//            }
//        }).collect(Collectors.toList());
//        return Response.ok(mobileList).build();
//    }
//
//    @DELETE
//    @Path("/{mobileToDelete}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response deleteMobile(@PathParam("mobileToDelete") String mobileName){
//        boolean isRemove = mobileList.remove(mobileName);
//        if(isRemove){
//            return Response.ok(mobileList).build();
//        }else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
//}

@Path("/mobile")
public class MobileResource{
    List<Mobile> mobileList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileList(){
        return Response.ok(mobileList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMobile(Mobile mobile){
        mobileList.add(mobile);
        return Response.ok(mobile).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMobile(@PathParam("id") int id,
                                 Mobile mobileToUpdate){
        mobileList = mobileList.stream().map(mobile ->
        {
            if(mobile.getId()==id){
                return mobileToUpdate;
            }else {
                return mobile;
            }
        }).collect(Collectors.toList());
        return Response.ok(mobileList).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMobile(@PathParam("id") int mobileIdToDelete){
        Optional<Mobile> mobileToDelete = mobileList.stream()
                .filter(mobile -> mobile.getId() == mobileIdToDelete)
                .findFirst();
        if(mobileToDelete.isPresent()){
            mobileList.remove(mobileToDelete.get());
            return Response.ok(mobileList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileById(@PathParam("id") int id){
        Optional<Mobile> optionalMobile = mobileList.stream()
                .filter(mobile -> mobile.getId() == id)
                .findFirst();
        if(optionalMobile.isPresent()){
            return Response.ok(optionalMobile.get()).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}