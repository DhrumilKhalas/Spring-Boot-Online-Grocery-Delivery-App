import React, { useState } from 'react';
import GroceryDisplay from '../../components/GroceryDisplay/GroceryDisplay';

const ExploreGrocery = () => {
  const [category, setCategory] = useState('All');
  const [searchText, setSearchText] = useState('');
  return (
    <>
      <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <form onSubmit={(e) => e.preventDefault()}>
            <div className="input-group mb-3">
              <select className='form-select mt-2' style={{'maxWidth': '150px'}} onChange={(e) => setCategory(e.target.value)}>
              <option value="All">All</option>
                <option value="Fresh Produce">Fresh Produce</option>
                <option value="Organic and Superfoods">Organic and Superfoods</option>
                <option value="Dairy Products">Dairy Products</option>
                <option value="Whole Grains and Legumes">Whole Grains and Legumes</option>
                <option value="Dry Fruits and Nuts">Dry Fruits and Nuts</option>
                <option value="Fresh Herbs and Spices">Fresh Herbs and Spices</option>
                <option value="Healthy Beverages">Healthy Beverages</option>
              </select>
              <input type="text" className='form-control mt-2' placeholder='Search your favorite grocery item...' 
                onChange={(e) => setSearchText(e.target.value)} value={searchText} />
              <button className='btn btn-primary mt-2' type='submit'>
                <i className='bi bi-search'></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <GroceryDisplay category={category} searchText={searchText} />
    </>

  )
}

export default ExploreGrocery;