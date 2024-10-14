import PostCard from "./PostCard";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";


const PostList = () => {
    const [post, setPost] = useState([]);
    const url = 'https://jsonplaceholder.typicode.com/posts';

    useEffect(() => {
        fetch(url)
        .then(response => response.json())
        .then(data => {setPost(data)})
        .catch(error => console.log('Error:', error));

    }, []);
    return (
        <>
        <h1>Post List</h1>
        <div>
                {post.map((post) => (
                    <PostCard
                     key={post.id}
                     title={post.title} 
                     id={post.id}
                     body={post.body} 
                        />
                ))}
        </div>
        </>
    );
    }

export default PostList;