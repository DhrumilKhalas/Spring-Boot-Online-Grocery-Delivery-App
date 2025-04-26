import React, { useState } from 'react';
import {assets} from '../../assets/assets';
import {addGrocery} from "../../services/groceryService";
import { toast } from 'react-toastify';

const AddGrocery = () => {
    const [image, setImage] = useState(false);
    const [data, setData] = useState({
        name:'',
        description: '',
        price:'',
        category: 'Organic and Superfoods'
    });

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setData(data => ({...data, [name]: value}));
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        if (!image) {
            toast.error('Please select an image.');
            return;
        }
        try {
            await addGrocery(data, image);
            toast.success('Grocery added successfully.');
            setData({name: '', description: '', category: 'Fresh Produce', price: ''});
            setImage(null);
        } catch (error) {
            toast.error('Error adding grocery.');
        }
    }

  return (
    <div className="mx-2 mt-2">
  <div className="row">
    <div className="card col-md-4">
      <div className="card-body">
        <h2 className="mb-4">Add Grocery</h2>
        <form onSubmit={onSubmitHandler}>
        <div className="mb-3">
            <label htmlFor="image" className="form-label">
                <img src={image ? URL.createObjectURL(image): assets.upload} alt="" width={98} />
            </label>
            <input type="file" className="form-control" id="image" hidden onChange={(e) => setImage(e.target.files[0])} />
          </div>
          <div className="mb-3">
            <label htmlFor="name" className="form-label">Name</label>
            <input type="text" placeholder='Chia Seeds' className="form-control" id="name" required name='name' onChange={onChangeHandler} value={data.name}/>
          </div>
          
          <div className="mb-3">
            <label htmlFor="description" className="form-label">Description</label>
            <textarea className="form-control" placeholder='Write content here...' id="description" rows="5" required name='description' onChange={onChangeHandler} value={data.description}></textarea>
          </div>
          <div className="mb-3">
            <label htmlFor="category" className="form-label">Category</label>
            <select name="category" id="category" className='form-control' onChange={onChangeHandler} value={data.category}>
                <option value="Fresh Produce">Fresh Produce</option>
                <option value="Organic and Superfoods">Organic and Superfoods</option>
                <option value="Dairy Products">Dairy Products</option>
                <option value="Whole Grains and Legumes">Whole Grains and Legumes</option>
                <option value="Dry Fruits and Nuts">Dry Fruits and Nuts</option>
                <option value="Fresh Herbs and Spices">Fresh Herbs and Spices</option>
                <option value="Healthy Beverages">Healthy Beverages</option>
            </select>
          </div>
          <div className="mb-3">
            <label htmlFor="price" className="form-label">Price</label>
            <input type="number" name="price" id="price" placeholder='&#8377;200' className='form-control' onChange={onChangeHandler} value={data.price} />
          </div>
          <button type="submit" className="btn btn-primary">Save</button>
        </form>
      </div>
    </div>
  </div>
</div>
  )
}

export default AddGrocery;

