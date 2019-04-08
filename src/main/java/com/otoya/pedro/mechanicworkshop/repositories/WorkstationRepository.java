/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.repositories;


import com.otoya.pedro.mechanicworkshop.domain.workstation.Workstation;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 *
 * @author PC
 */

public interface WorkstationRepository extends MongoRepository<Workstation, Long>{
}
