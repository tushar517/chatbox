package com.example.chatbox.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage


val supabase = createSupabaseClient(
    supabaseUrl = "https://kdntdrzmecnmryrvqexg.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtkbnRkcnptZWNubXJ5cnZxZXhnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDAyMDkwNjQsImV4cCI6MjA1NTc4NTA2NH0.hLVwEfgKbSzQSu55c4kKv72www5HFp_2S3396elL5W0"
) {
    install(Storage){
    }
}
