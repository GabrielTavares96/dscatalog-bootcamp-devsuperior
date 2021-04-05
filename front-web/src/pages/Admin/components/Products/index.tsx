import React from 'react';
import { Route, Switch } from 'react-router';
import Form from './Form';
import List from './List';
import './styles.scss';

const Products = () => {

    return(
        <div>
            <Switch>
                <Route path="/admin/products" exact>
                    <List />
                </Route>
                <Route path="/admin/products/create">
                    <Form />
                </Route>
                <Route path="/admin/products/:productId">
                    <h1>Editar produtos</h1>
                </Route>
            </Switch>
        </div>
    );
}

export default Products;