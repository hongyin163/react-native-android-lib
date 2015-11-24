
var { 
	requireNativeComponent,
	PropTypes 
} = require('react-native');

var iface = {
  name: 'ScrollViewPullToRefresh',
  propTypes: {  
  	refreshing:PropTypes.bool,
  	scaleX: PropTypes.number,
  	scaleY: PropTypes.number,
  	translateX: PropTypes.number,
  	translateY: PropTypes.number,
  	rotation: PropTypes.number,
  },
};

class MyCustomView extends React.Component {
  constructor() {
    this._onChange = this._onChange.bind(this);
  }
  _onChange(event: Event) {
    if (!this.props.onRefresh) {
      return;
    }
    this.props.onRefresh();
  }
  render() {
    return <ScrollViewPullToRefresh {...this.props} onLoadingStart={this._onChange} />;
  }
}
MyCustomView.propTypes = {
  /**
   * Callback that is called continuously when the user is dragging the map.
   */
    onRefresh: React.PropTypes.func,
    refreshing:PropTypes.bool,
    scaleX: PropTypes.number,
    scaleY: PropTypes.number,
    translateX: PropTypes.number,
    translateY: PropTypes.number,
    rotation: PropTypes.number,
};


var ScrollViewPullToRefresh=requireNativeComponent('ScrollViewPullToRefresh', iface,{
  nativeOnly: {onLoadingStart: true}
});

 module.exports =MyCustomView