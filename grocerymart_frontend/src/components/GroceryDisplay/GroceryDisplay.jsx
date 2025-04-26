import React, { useContext } from 'react';
import { StoreContext } from '../../context/StoreContext';
import GroceryItem from '../GroceryItem/GroceryItem';

const GroceryDisplay = ({category, searchText}) => {

    const {groceryList} = useContext(StoreContext);
    const filteredGroceries = groceryList.filter(grocery => (
        (category === 'All' || grocery.category === category) &&
        grocery.name.toLowerCase().includes(searchText.toLowerCase())
    ));
  return (
    <div className="container">
        <div className="row">
            {filteredGroceries.length > 0 ? (
                filteredGroceries.map((grocery, index) => (
                    <GroceryItem key={index} 
                        name={grocery.name} 
                        description={grocery.description}
                        id={grocery.id}
                        imageUrl={grocery.imageUrl}
                        price={grocery.price} />
                ))
            ) : (
                <div className="text-center mt-4">
                    <h4>No grocery found.</h4>
                </div>
            )}
        </div> 
    </div>
  )
}

export default GroceryDisplay;