package com.example.workshopmongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.workshopmongodb.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Post> searchTitle(String text);

    //é um método de consulta derivada no Spring Data JPA ou Spring Data MongoDB.
    //É quando o nome do método já indica automaticamente ao Spring qual consulta deve ser executada, sem precisar escrever uma query manualmente.
    List<Post> findByTitleContainingIgnoreCase(String text);

    @Query("{ $and: [ {date: {$gte: ?1} }, {date: {$lte: ?2 } },{ $or: [ {'title': { $regex: ?0 ,$options: 'i'}}, {'body': { $regex: ?0 ,$options: 'i'}},{'comments.text': { $regex: ?0 ,$options: 'i'}} ] } ] }")
    List<Post> fullSearch(String text, Date minDate, Date maxDate);

}
