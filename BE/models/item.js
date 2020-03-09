var mongoose = require('mongoose');

var itemSchema = mongoose.Schema({
  manufactdate: { type: String, required: true },
  expirationdate: { type: Date, required: true },
  type: { type: String, required: true },
  information: { type: String, required: true },
  originprice: { type: String, required: true },
  saleprice: { type: String, required: true }
});

module.exports = mongoose.model('Item', itemSchema);