package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Origin;

public interface IOriginDao {

    // get a origin by its id
    public Origin getOne(Long id);
    
    public Origin getOneDetached(Long id);

    // get all origins
    public List<Origin> getAll();

    // save a origin
    public Origin saveOne(Origin origin);

    // update a origin
    public Origin updateOne(Origin origin);

    // delete a origin by its id
    public void deleteOne(Long id);

   

}
