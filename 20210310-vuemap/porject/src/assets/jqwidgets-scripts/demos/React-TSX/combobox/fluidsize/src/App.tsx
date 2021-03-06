import * as React from 'react';
 


import './App.css';

import JqxComboBox, { IComboBoxProps } from 'jqwidgets-scripts/jqwidgets-react-tsx/jqxcombobox';

class App extends React.PureComponent<{}, IComboBoxProps> {

    constructor(props: {}) {
        super(props);

        this.state = {
            source: [
                'Affogato',
                'Americano',
                'Bicerin',
                'Breve',
                'Café Bombón',
                'Café au lait',
                'Caffé Corretto',
                'Café Crema',
                'Caffé Latte',
                'Caffé macchiato',
                'Café mélange',
                'Coffee milk',
                'Cafe mocha',
                'Cappuccino',
                'Carajillo',
                'Cortado',
                'Cuban espresso',
                'Espresso',
                'Eiskaffee',
                'The Flat White',
                'Frappuccino',
                'Galao',
                'Greek frappé coffee',
                'Iced Coffee﻿',
                'Indian filter coffee',
                'Instant coffee',
                'Irish coffee',
                'Liqueur coffee'
            ]
        }
    }

    public render() {
        return (
            <JqxComboBox theme={'material-purple'} 
                width={'100%'} height={35} source={this.state.source} selectedIndex={0} />
        );
    }
}

export default App;