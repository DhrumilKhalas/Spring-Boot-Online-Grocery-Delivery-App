import axios from "axios";

const API_URL = 'http://localhost:8080/api/groceries';

export const fetchGroceryList = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.log('Error fetching grocery list:', error);
        throw error;
    }    
}

export const fetchGroceryDetails = async (id) => {
    try {
        const response = await axios.get(API_URL+"/"+id);
        return response.data;
    } catch (error) {
        console.log('Error fetching grocery details:', error);
        throw error;
    }
    
}