package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
    public Song findByName(String name);
    //findByName present in jpa which excute query on basis of string name parameter
}