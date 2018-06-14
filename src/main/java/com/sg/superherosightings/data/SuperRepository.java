/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Super;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author amanda
 */
@Repository
public interface SuperRepository extends JpaRepository<Super, Integer> {
    
    Super findByName(String name);
    
}
