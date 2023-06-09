package com.hddev.portfoliobackend.features.posts.services

import com.hddev.portfoliobackend.features.user.repositories.UserRepository
import com.hddev.portfoliobackend.features.posts.model.PostEntity
import com.hddev.portfoliobackend.features.posts.repositories.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository,
                  private val userRepository: UserRepository) {

    fun getAllPosts(): List<PostEntity> = postRepository.findAll()

    fun getPostById(id: Long): PostEntity? = postRepository.findById(id).orElse(null)

    fun createPost(post: PostEntity, username: String): PostEntity {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            post.author = user
            return postRepository.save(post)
        } else {
            throw Exception("User not found.")
        }
    }

    fun deletePost(id: Long, username: String) {
        val post = getPostById(id)
        val user = userRepository.findByUsername(username)
        if (post != null) {
            if (post.author == user) {
                postRepository.deleteById(id)
            } else {
                throw Exception("Permission denied.")
            }
        } else {
            throw Exception("Post not found.")
        }
    }
}
