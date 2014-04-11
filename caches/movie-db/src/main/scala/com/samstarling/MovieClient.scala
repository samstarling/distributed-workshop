package com.samstarling

class MovieClient {
  val base = "https://api.themoviedb.org/3/"
  val key = "?api_key=89e5511513f926ee8ac9569963afa8f2"

  def getPopular: String = {
    val cacheKey = "movie/popular"
    Cache.getOrPopulate(cacheKey) {
      HttpClient.get(s"${base}movie/popular${key}")
    }
  }

  def getDetailById(id: String) = {
    val cacheKey = s"movie/${id}"
    Cache.getOrPopulate(cacheKey) {
      HttpClient.get(s"${base}movie/${id}${key}")
    }
  }
}



